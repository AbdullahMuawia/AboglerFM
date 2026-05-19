import { useEffect, useMemo, useState } from 'react'
import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import PageShell from '../components/PageShell'
import { getTopTracks } from '../api/analytics'

export default function TopTracks() {
  const [data, setData] = useState([])

  useEffect(() => {
    getTopTracks().then(res => setData(res.data))
  }, [])

  const chartData = useMemo(() => {
    return data.slice(0, 8).map(t => ({
      label: `${t.name} - ${t.artist}`,
      plays: t.plays
    }))
  }, [data])

  return (
    <PageShell title="Top Tracks" subtitle="Your most replayed tracks">
      <section className="card chart-card">
        <div className="card-head">
          <h2 className="section-title">Top 8 tracks</h2>
          <span className="chip">Plays</span>
        </div>
        <div className="chart-shell">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={chartData}>
              <CartesianGrid stroke="rgba(28,20,14,0.18)" strokeDasharray="4 4" />
              <XAxis dataKey="label" tick={{ fill: 'var(--ink-muted)', fontSize: 11 }} interval={0} angle={-18} textAnchor="end" height={80} />
              <YAxis tick={{ fill: 'var(--ink-muted)', fontSize: 12 }} />
              <Tooltip contentStyle={{ background: 'var(--panel-strong)', border: '1px solid var(--border)', borderRadius: 12 }} />
              <Bar dataKey="plays" fill="var(--accent-2)" radius={[6, 6, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </section>

      <section className="card" style={{ marginTop: 24 }}>
        <div className="card-head">
          <h2 className="section-title">All tracks</h2>
          <span className="chip">Full list</span>
        </div>
        <div className="list" style={{ marginTop: 16 }}>
          {data.map((t, i) => (
            <div key={i} className="list-row">
              <div>{t.name} - {t.artist}</div>
              <div className="muted">{t.plays} plays</div>
            </div>
          ))}
        </div>
      </section>
    </PageShell>
  )
}
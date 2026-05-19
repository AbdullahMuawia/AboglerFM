import { useEffect, useMemo, useState } from 'react'
import { Bar, BarChart, CartesianGrid, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import PageShell from '../components/PageShell'
import { getTopArtists } from '../api/analytics'

export default function TopArtists() {
  const [data, setData] = useState([])

  useEffect(() => {
    getTopArtists().then(res => setData(res.data))
  }, [])

  const chartData = useMemo(() => data.slice(0, 10), [data])

  return (
    <PageShell title="Top Artists" subtitle="Your most played artists">
      <section className="card chart-card">
        <div className="card-head">
          <h2 className="section-title">Top 10 artists</h2>
          <span className="chip">Plays</span>
        </div>
        <div className="chart-shell">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={chartData}>
              <CartesianGrid stroke="rgba(28,20,14,0.18)" strokeDasharray="4 4" />
              <XAxis dataKey="artist" tick={{ fill: 'var(--ink-muted)', fontSize: 12 }} interval={0} angle={-18} textAnchor="end" height={70} />
              <YAxis tick={{ fill: 'var(--ink-muted)', fontSize: 12 }} />
              <Tooltip contentStyle={{ background: 'var(--panel-strong)', border: '1px solid var(--border)', borderRadius: 12 }} />
              <Bar dataKey="plays" fill="var(--accent)" radius={[6, 6, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </section>

      <section className="card" style={{ marginTop: 24 }}>
        <div className="card-head">
          <h2 className="section-title">All artists</h2>
          <span className="chip">Full list</span>
        </div>
        <div className="list" style={{ marginTop: 16 }}>
          {data.map((a, i) => (
            <div key={i} className="list-row">
              <div>{a.artist}</div>
              <div className="muted">{a.plays} plays</div>
            </div>
          ))}
        </div>
      </section>
    </PageShell>
  )
}
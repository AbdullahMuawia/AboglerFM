import { useEffect, useState } from 'react'
import { CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import PageShell from '../components/PageShell'
import { getTimeline } from '../api/analytics'

export default function Timeline() {
  const [data, setData] = useState([])

  useEffect(() => {
    getTimeline().then(res => setData(res.data))
  }, [])

  return (
    <PageShell title="Listening Timeline" subtitle="Daily play counts over time">
      <section className="card chart-card">
        <div className="card-head">
          <h2 className="section-title">Timeline</h2>
          <span className="chip">Daily plays</span>
        </div>
        <div className="chart-shell">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={data}>
              <CartesianGrid stroke="rgba(28,20,14,0.18)" strokeDasharray="4 4" />
              <XAxis dataKey="day" tick={{ fill: 'var(--ink-muted)', fontSize: 12 }} />
              <YAxis tick={{ fill: 'var(--ink-muted)', fontSize: 12 }} allowDecimals={false} />
              <Tooltip contentStyle={{ background: 'var(--panel-strong)', border: '1px solid var(--border)', borderRadius: 12 }} />
              <Line type="monotone" dataKey="plays" stroke="var(--accent)" strokeWidth={2} dot={{ r: 3 }} />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </section>
    </PageShell>
  )
}
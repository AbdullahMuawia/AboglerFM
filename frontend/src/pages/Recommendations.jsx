import { useEffect, useState } from 'react'
import PageShell from '../components/PageShell'
import { getRecommendations } from '../api/recommendations'

export default function Recommendations() {
  const [data, setData] = useState([])
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const controller = new AbortController()

    setLoading(true)
    setError(null)

    getRecommendations(controller.signal)
      .then(res => {
        setData(res.data.recommendations || [])
      })
      .catch(err => {
        if (err.name !== 'CanceledError' && err.code !== 'ERR_CANCELED') {
          setError('Could not load recommendations')
        }
      })
      .finally(() => setLoading(false))

    return () => controller.abort()
  }, [])

  return (
    <PageShell title="AI Recommendations" subtitle="Personalized picks from your listening data">
      <section className="card">
        <div className="card-head">
          <h2 className="section-title">Your picks</h2>
          <span className="chip">AI session</span>
        </div>

        {loading && <p className="muted">Loading recommendations...</p>}
        {error && <p className="muted">{error}</p>}

        <div className="rec-grid" style={{ marginTop: 16 }}>
          {data.map((r, i) => (
            <article key={i} className="rec-card">
              <div className="rec-title">{r.song || 'Untitled'}</div>
              <div className="rec-artist">{r.artist || 'Unknown artist'}</div>
              <div className="rec-reason">{r.reason || 'No note provided.'}</div>
            </article>
          ))}
        </div>
      </section>
    </PageShell>
  )
}
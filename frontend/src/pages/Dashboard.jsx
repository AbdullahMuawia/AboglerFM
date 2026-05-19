import { useMemo, useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import PageShell from '../components/PageShell'
import { getHistory, syncHistory } from '../api/music'

export default function Dashboard() {
  const navigate = useNavigate()
  const username = localStorage.getItem('username')
  const [tracks, setTracks] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    let cancelled = false
    setLoading(true)
    setError(null)

    syncHistory()
      .then(() => getHistory())
      .then(res => {
        if (!cancelled) setTracks(res.data)
      })
      .catch(() => {
        if (!cancelled) setError('Could not load history')
      })
      .finally(() => {
        if (!cancelled) setLoading(false)
      })

    return () => { cancelled = true }
  }, [])

  const stats = useMemo(() => {
    if (!tracks.length) return { total: 0, topArtist: 'None yet' }
    const counts = tracks.reduce((acc, t) => {
      acc[t.artist] = (acc[t.artist] || 0) + 1
      return acc
    }, {})
    const topArtist = Object.entries(counts).sort((a, b) => b[1] - a[1])[0]?.[0] || 'None'
    return { total: tracks.length, topArtist }
  }, [tracks])

  const handleLogout = () => {
    localStorage.removeItem('jwt')
    localStorage.removeItem('username')
    navigate('/')
  }

  return (
    <PageShell
      title="Dashboard"
      subtitle={`Logged in as ${username}`}
      actions={<button className="btn" onClick={handleLogout}>Logout</button>}
    >
      <section className="stats-grid">
        <div className="stat-card">
          <div className="stat-label">Total plays</div>
          <div className="stat-value">{stats.total}</div>
        </div>
        <div className="stat-card">
          <div className="stat-label">Top artist</div>
          <div className="stat-value">{stats.topArtist}</div>
        </div>
      </section>

      <section className="card" style={{ marginTop: 24 }}>
        <div className="card-head">
          <h2 className="section-title">Recent plays</h2>
          <span className="chip">Last 50</span>
        </div>

        {loading && <p className="muted">Loading...</p>}
        {error && <p className="muted">{error}</p>}

        <div className="list" style={{ marginTop: 16 }}>
          {tracks.map((track, i) => (
            <div key={i} className="list-row">
              <div className="list-left">
                {track.imageUrl && (
                  <img src={track.imageUrl} alt={track.name} className="cover" />
                )}
                <div>
                  <div>{track.name}</div>
                  <div className="muted">{track.artist} - {track.album}</div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>
    </PageShell>
  )
}
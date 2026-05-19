import { useState } from 'react'
import axios from 'axios'

export default function Login() {
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const handleConnect = async () => {
    setLoading(true)
    setError(null)
    try {
      const res = await axios.get('http://localhost:8080/auth/lastfm/login-url')
      window.location.href = res.data.url
    } catch (err) {
      setError('Could not start login. Is the backend running?')
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-hero">
          <div className="vinyl" />
        </div>

        <h1 className="auth-title">AboglerFM</h1>
        <p className="auth-lead">Analog insight for your listening history.</p>

        <button onClick={handleConnect} disabled={loading} className="btn" style={{ marginTop: 18 }}>
          {loading ? 'Connecting...' : 'Connect with Last.fm'}
        </button>

        {error && <p className="muted" style={{ marginTop: 12 }}>{error}</p>}
        <p className="auth-note" style={{ marginTop: 18 }}>Secure OAuth. No password stored.</p>
      </div>
    </div>
  )
}
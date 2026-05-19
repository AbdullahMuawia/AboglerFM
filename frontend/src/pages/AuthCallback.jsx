import { useEffect, useState } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import axios from 'axios'

export default function AuthCallback() {
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const [error, setError] = useState(null)

  useEffect(() => {
    const token = searchParams.get('token')
    if (!token) {
      setError('No token received from Last.fm')
      return
    }

    const guardKey = `lastfm_token_used:${token}`
    if (sessionStorage.getItem(guardKey)) {
      return
    }
    sessionStorage.setItem(guardKey, '1')

    axios
      .get('http://localhost:8080/auth/lastfm/callback', { params: { token } })
      .then(res => {
        localStorage.setItem('jwt', res.data.token)
        localStorage.setItem('username', res.data.username)
        navigate('/dashboard')
      })
      .catch(() => setError('Login failed. Please try again.'))
  }, [searchParams, navigate])

  return (
    <div className="auth-page">
      <div className="auth-card auth-card--compact">
        <div className="auth-hero">
          <div className="vinyl" />
        </div>
        {error ? (
          <>
            <p className="muted">{error}</p>
            <button
              onClick={() => navigate('/')}
              className="btn"
              style={{ marginTop: 16 }}
            >
              Back to login
            </button>
          </>
        ) : (
          <>
            <p className="auth-lead">Signing you in...</p>
            <p className="auth-note" style={{ marginTop: 8 }}>Spinning the tapes</p>
          </>
        )}
      </div>
    </div>
  )
}
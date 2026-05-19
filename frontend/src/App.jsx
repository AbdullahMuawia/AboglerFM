import { Navigate, BrowserRouter, Routes, Route } from 'react-router-dom'
import Dashboard from './pages/Dashboard'
import Login from './pages/Login'
import AuthCallback from './pages/AuthCallback'
import TopArtists from './pages/TopArtists'
import TopTracks from './pages/TopTracks'
import Timeline from './pages/Timeline'
import Recommendations from './pages/Recommendations'

function PublicRoute({ children }) {
  const token = localStorage.getItem('jwt')
  return token ? <Navigate to="/dashboard" /> : children
}

function PrivateRoute({ children }) {
  const token = localStorage.getItem('jwt')
  return token ? children : <Navigate to="/" />
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PublicRoute><Login /></PublicRoute>} />
        <Route path="/auth/lastfm/callback" element={<AuthCallback />} />
        <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/top-artists" element={<PrivateRoute><TopArtists /></PrivateRoute>} />
        <Route path="/top-tracks" element={<PrivateRoute><TopTracks /></PrivateRoute>} />
        <Route path="/timeline" element={<PrivateRoute><Timeline /></PrivateRoute>} />
        <Route path="/recommendations" element={<PrivateRoute><Recommendations /></PrivateRoute>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
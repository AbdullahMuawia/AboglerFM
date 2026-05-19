import api from './musicApi'

export const getTopArtists = () => api.get('/api/analytics/top-artists')
export const getTopTracks = () => api.get('/api/analytics/top-tracks')
export const getTimeline = () => api.get('/api/analytics/timeline')
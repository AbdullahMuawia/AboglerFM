import api from './musicApi'

export const getRecommendations = (signal) => 
  api.get('/api/recommendations', { signal })
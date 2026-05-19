import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080'
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('jwt')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  response => response,
  error => {
 
    if (error.response?.status === 401 && !error.config?._skipAuthRedirect) {
      localStorage.removeItem('jwt')
      localStorage.removeItem('username')
      window.location.href = '/'
    }
    return Promise.reject(error)
  }
)

export default api
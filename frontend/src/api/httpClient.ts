import axios from 'axios'
import { clearToken, getToken } from './tokenStorage'

const AUTH_ENDPOINTS = ['/api/auth/login', '/api/auth/register']

export const httpClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080',
})

httpClient.interceptors.request.use((config) => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

httpClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const isAuthEndpoint = AUTH_ENDPOINTS.some((endpoint) =>
      error.config?.url?.includes(endpoint),
    )
    if (error.response?.status === 401 && !isAuthEndpoint) {
      clearToken()
      window.location.assign('/login')
    }
    return Promise.reject(error)
  },
)

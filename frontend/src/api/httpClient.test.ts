import MockAdapter from 'axios-mock-adapter'
import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { httpClient } from './httpClient'
import { clearToken, setToken } from './tokenStorage'

describe('httpClient', () => {
  let mock: MockAdapter
  let assignSpy: ReturnType<typeof vi.fn>

  beforeEach(() => {
    mock = new MockAdapter(httpClient)
    assignSpy = vi.fn()
    Object.defineProperty(window, 'location', {
      value: { assign: assignSpy },
      writable: true,
    })
  })

  afterEach(() => {
    mock.restore()
    clearToken()
  })

  it('attaches the Authorization header when a token is stored', async () => {
    setToken('test-jwt')
    mock.onGet('/api/family-members').reply((config) => {
      expect(config.headers?.Authorization).toBe('Bearer test-jwt')
      return [200, []]
    })

    await httpClient.get('/api/family-members')
  })

  it('does not attach an Authorization header when no token is stored', async () => {
    mock.onGet('/api/family-members').reply((config) => {
      expect(config.headers?.Authorization).toBeUndefined()
      return [200, []]
    })

    await httpClient.get('/api/family-members')
  })

  it('clears the token and redirects to /login on a 401 from a protected endpoint', async () => {
    setToken('expired-jwt')
    mock.onGet('/api/family-members').reply(401)

    await expect(httpClient.get('/api/family-members')).rejects.toBeTruthy()
    expect(assignSpy).toHaveBeenCalledWith('/login')
  })

  it('does not redirect on a 401 from the login endpoint', async () => {
    mock.onPost('/api/auth/login').reply(401)

    await expect(
      httpClient.post('/api/auth/login', { email: 'a@b.com', password: 'wrong' }),
    ).rejects.toBeTruthy()
    expect(assignSpy).not.toHaveBeenCalled()
  })
})

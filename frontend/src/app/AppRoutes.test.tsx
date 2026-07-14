import { render, screen } from '@testing-library/react'
import { afterEach, describe, expect, it } from 'vitest'
import App from '../App'

function renderAtPath(path: string) {
  window.history.pushState({}, '', path)
  return render(<App />)
}

describe('App routing', () => {
  afterEach(() => {
    window.history.pushState({}, '', '/')
  })

  it('renders the login page on the public /login route', () => {
    renderAtPath('/login')
    expect(screen.getByRole('heading', { name: 'Connexion' })).toBeInTheDocument()
  })

  it('renders the register page on the public /register route', () => {
    renderAtPath('/register')
    expect(
      screen.getByRole('heading', { name: 'Inscription' }),
    ).toBeInTheDocument()
  })

  it('renders the treatments page under the app layout', () => {
    renderAtPath('/traitements')
    expect(screen.getByRole('heading', { name: 'Traitements' })).toBeInTheDocument()
    expect(
      screen.getByRole('navigation', { name: 'Navigation principale' }),
    ).toBeInTheDocument()
  })
})

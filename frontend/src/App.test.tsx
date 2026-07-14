import { render, screen } from '@testing-library/react'
import { describe, expect, it } from 'vitest'
import App from './App'

describe('App', () => {
  it('renders the dashboard on the default route', () => {
    render(<App />)
    expect(
      screen.getByRole('heading', { name: 'Tableau de bord' }),
    ).toBeInTheDocument()
  })

  it('renders navigation links to every module', () => {
    render(<App />)
    const nav = screen.getByRole('navigation', { name: 'Navigation principale' })
    expect(nav).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Traitements' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Famille' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Constantes' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Sécurité' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Assistant' })).toBeInTheDocument()
    expect(
      screen.getByRole('link', { name: 'Médicaments & Prix' }),
    ).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Santé' })).toBeInTheDocument()
    expect(screen.getByRole('link', { name: 'Paramètres' })).toBeInTheDocument()
  })
})

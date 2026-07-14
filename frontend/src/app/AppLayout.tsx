import { NavLink, Outlet } from 'react-router-dom'

const NAV_LINKS = [
  { to: '/', label: 'Tableau de bord', end: true },
  { to: '/traitements', label: 'Traitements' },
  { to: '/famille', label: 'Famille' },
  { to: '/constantes', label: 'Constantes' },
  { to: '/securite', label: 'Sécurité' },
  { to: '/assistant', label: 'Assistant' },
  { to: '/medicaments', label: 'Médicaments & Prix' },
  { to: '/sante', label: 'Santé' },
  { to: '/parametres', label: 'Paramètres' },
]

function AppLayout() {
  return (
    <div>
      <nav aria-label="Navigation principale">
        <ul>
          {NAV_LINKS.map(({ to, label, end }) => (
            <li key={to}>
              <NavLink to={to} end={end}>
                {label}
              </NavLink>
            </li>
          ))}
        </ul>
      </nav>
      <main>
        <Outlet />
      </main>
    </div>
  )
}

export default AppLayout

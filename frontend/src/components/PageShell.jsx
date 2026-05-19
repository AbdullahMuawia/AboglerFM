import { NavLink } from 'react-router-dom'

const navItems = [
  { to: '/dashboard', label: 'Dashboard' },
  { to: '/top-artists', label: 'Top Artists' },
  { to: '/top-tracks', label: 'Top Tracks' },
  { to: '/timeline', label: 'Timeline' },
  { to: '/recommendations', label: 'Recommendations' }
]

export default function PageShell({ title, subtitle, actions, children }) {
  return (
    <div className="page">
      <div className="container">
        <header className="page-header">
          <div className="header-top">
            <div className="brand">
              <div className="brand-mark" aria-hidden="true" />
              <div>
                <div className="brand-name">AboglerFM</div>
                <div className="brand-tag">Analog listening desk</div>
              </div>
            </div>
            {actions && <div className="header-actions">{actions}</div>}
          </div>

          <div>
            <h1 className="page-title">{title}</h1>
            {subtitle && <p className="page-subtitle">{subtitle}</p>}
          </div>

          <nav className="nav-pills">
            {navItems.map(item => (
              <NavLink
                key={item.to}
                to={item.to}
                className={({ isActive }) =>
                  `nav-pill ${isActive ? 'nav-pill--active' : ''}`
                }
              >
                {item.label}
              </NavLink>
            ))}
          </nav>
        </header>

        <div className="fade-up">{children}</div>
      </div>
    </div>
  )
}
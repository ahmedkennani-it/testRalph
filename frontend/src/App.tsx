import { BrowserRouter, Route, Routes } from 'react-router-dom'
import AppLayout from './app/AppLayout'
import AssistantPage from './features/assistant/AssistantPage'
import LoginPage from './features/auth/LoginPage'
import RegisterPage from './features/auth/RegisterPage'
import DashboardPage from './features/dashboard/DashboardPage'
import FamilyPage from './features/family/FamilyPage'
import HealthPage from './features/health/HealthPage'
import HealthConstantsPage from './features/healthConstants/HealthConstantsPage'
import MedicationsPage from './features/medications/MedicationsPage'
import SafetyPage from './features/safety/SafetyPage'
import SettingsPage from './features/settings/SettingsPage'
import TreatmentsPage from './features/treatments/TreatmentsPage'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route element={<AppLayout />}>
          <Route path="/" element={<DashboardPage />} />
          <Route path="/traitements" element={<TreatmentsPage />} />
          <Route path="/famille" element={<FamilyPage />} />
          <Route path="/constantes" element={<HealthConstantsPage />} />
          <Route path="/securite" element={<SafetyPage />} />
          <Route path="/assistant" element={<AssistantPage />} />
          <Route path="/medicaments" element={<MedicationsPage />} />
          <Route path="/sante" element={<HealthPage />} />
          <Route path="/parametres" element={<SettingsPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App

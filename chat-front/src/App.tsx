
import { AuthProvider } from './context/AuthContext';
import { AppRoutes } from './router/routes';

function App() {
  return (
    <AuthProvider>
      <AppRoutes/>
    </AuthProvider>
  )
}

export default App

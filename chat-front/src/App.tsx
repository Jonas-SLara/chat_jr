
import { AuthProvider } from './context/AuthContext';
import { AppRoutes } from './router/routes';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

const queryClient = new QueryClient;

function App() {
  return (
    <AuthProvider>
      <QueryClientProvider client={queryClient}>
        <AppRoutes/>
      </QueryClientProvider>
    </AuthProvider>
  )
}

export default App

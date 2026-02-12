import { lazy, type ReactNode, Suspense } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";

const Auth = lazy(() => import('../pages/Auth'));
const Dashboard = lazy(() => import('../pages/Dash'));

function PrivateRoute({children}: {children: ReactNode}){
    const token = localStorage.getItem("token");
    if (!token) {
        return <Navigate to="/" />;
    }

    return children;
}

export function AppRoutes() {
  return (
    <BrowserRouter>
      <Suspense fallback={<div>Loading...</div>}>
        <Routes>
          <Route path="/" element={<Auth/>} />
          <Route path="/register" element={<Dashboard/>} />

          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <Dashboard />
              </PrivateRoute>
            }
          />
        </Routes>
      </Suspense>
    </BrowserRouter>
  );
}

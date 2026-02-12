import { lazy, Suspense } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import AuthLayout from "../layouts/AuthLayout";
import AppLayout from "../layouts/AppLayout";

const AuthPage = lazy(() => import('../pages/Auth'));
const DashBoardPage = lazy(() => import('../pages/Dash'));


export function AppRoutes() {
  return (
    <BrowserRouter>
      <Suspense fallback={<div>Loading ...</div>}>
        <Routes>

          <Route element={<AuthLayout/>} path="/">
            <Route index element={<AuthPage/>} />
          </Route>

          <Route element={<AppLayout/>} path="/app">
            <Route index element={<DashBoardPage/>} />
          </Route>

        </Routes>
      </Suspense>
    </BrowserRouter>
  );
}

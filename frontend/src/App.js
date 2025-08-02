import React, { useEffect } from "react";
import { useNavigate, Routes, Route, Navigate } from "react-router-dom";
import UserLogin from "./pages/UserManagement/UserLogin";
import UserRegister from "./pages/UserManagement/UserRegister";
import UpdateUserProfile from "./pages/UserManagement/UpdateUserProfile";
import NotificationsPage from "./pages/NotificationManagement/NotificationsPage";
import AddNewPost from "./pages/PostManagement/AddNewPost";
import AllPost from "./pages/PostManagement/AllPost";
import UpdatePost from "./pages/PostManagement/UpdatePost";
import UserProfile from "./pages/UserManagement/UserProfile";
import MyPost from "./pages/PostManagement/MyPost";
import MyLearningPlan from "./pages/LearningPlans/MyLearningPlans.js";
import UpdateLearningPlan from "./pages/LearningPlans/UpdateLearningPlan.js";
import AllLearningPlan from "./pages/LearningPlans/AllLearningPlans.js";
import AddLearningPlan from "./pages/LearningPlans/AddLearningPlans.js";

function ProtectedRoute({ children }) {
  const userID = localStorage.getItem("userID");
  if (!userID) {
    return <Navigate to="/" />;
  }
  return children;
}

function App() {
  const navigate = useNavigate();

  useEffect(() => {
    if (window.location.pathname === "/oauth2/success") {
      const params = new URLSearchParams(window.location.search);
      const userID = params.get("userID");
      const name = params.get("name");

      if (userID && name) {
        localStorage.setItem("userID", userID);
        alert(`Login successful! Welcome, ${name}`);
        localStorage.setItem("userType", "google");
        navigate("/allPost");
      } else {
        alert("Login failed. Missing user information.");
      }
    }
  }, [navigate]);

  return (
    <div>
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<UserLogin />} />
        <Route path="/register" element={<UserRegister />} />
        {/* Protected Routes */}
        <Route
          path="/updateUserProfile/:id"
          element={
            <ProtectedRoute>
              <UpdateUserProfile />
            </ProtectedRoute>
          }
        />
        <Route
          path="/userProfile"
          element={
            <ProtectedRoute>
              <UserProfile />
            </ProtectedRoute>
          }
        />
        <Route
          path="/notifications"
          element={
            <ProtectedRoute>
              <NotificationsPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/addNewPost"
          element={
            <ProtectedRoute>
              <AddNewPost />
            </ProtectedRoute>
          }
        />
        <Route
          path="/my-posts"
          element={
            <ProtectedRoute>
              <MyPost />
            </ProtectedRoute>
          }
        />
        <Route
          path="/allPost"
          element={
            <ProtectedRoute>
              <AllPost />
            </ProtectedRoute>
          }
        />
        <Route
          path="/updatePost/:id"
          element={
            <ProtectedRoute>
              <UpdatePost />
            </ProtectedRoute>
          }
        />
        <Route
          path="/learningPlan"
          element={
            <ProtectedRoute>
              <AllLearningPlan />
            </ProtectedRoute>
          }
        />
        <Route
          path="/addLearningPlan"
          element={
            <ProtectedRoute>
              <AddLearningPlan />
            </ProtectedRoute>
          }
        />
        <Route
          path="/updateLearningPlan/:id"
          element={
            <ProtectedRoute>
              <UpdateLearningPlan />
            </ProtectedRoute>
          }
        />
        <Route
          path="/myPlan"
          element={
            <ProtectedRoute>
              <MyLearningPlan />
            </ProtectedRoute>
          }
        />
      </Routes>
    </div>
  );
}

export default App;

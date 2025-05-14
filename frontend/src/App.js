import React, { useEffect } from "react";
import { Route, Routes, Navigate } from "react-router";
import { useNavigate } from "react-router-dom";
import UserLogin from "./Pages/UserManagement/UserLogin";
import UserRegister from "./Pages/UserManagement/UserRegister";
import UpdateUserProfile from "./Pages/UserManagement/UpdateUserProfile";
import NotificationsPage from "./Pages/NotificationManagement/NotificationsPage";
import AddNewPost from "./Pages/PostManagement/AddNewPost";
import AllPost from "./Pages/PostManagement/AllPost";
import UpdatePost from "./Pages/PostManagement/UpdatePost";
import UserProfile from "./Pages/UserManagement/UserProfile";
import MyPost from "./Pages/PostManagement/MyPost";
import AllLearningPlan from "./Pages/learningPlan/AllLearningPlan";
import AddLearningPlan from "./Pages/LearningPlan/AddLearningPlan";
import UpdateLearningPlan from "./Pages/LearningPlan/UpdateLearningPlan";
import MyLearningPlan from "./Pages/LearningPlan/MyLearningPlan";


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
      <React.Fragment>
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
      </React.Fragment>
    </div>
  );
}

export default App;

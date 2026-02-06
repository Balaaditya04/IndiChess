import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import SideNav from "../components/SideNav";
import Header from "../components/Header";
import GameInfo from "../components/game-page-components/GameInfo";

function HomePage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');

    if (!token) {
      navigate('/');
      return;
    }

    // Validate JWT by calling a protected endpoint
    const validateToken = async () => {
      try {
        await axios.get('http://localhost:8080/auth/user', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        setLoading(false);
      } catch (error) {
        console.error('Token validation failed:', error);
        localStorage.removeItem('token');
        navigate('/');
      }
    };

    validateToken();
  }, [navigate]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="app-container">
      <SideNav /> {/* Render the SideNav */}
      <div className="main-container">
        <Header />
        <div className="game-info-container">
          <GameInfo />
        </div>
      </div>

    </div>

  );
}

export default HomePage;

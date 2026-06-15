import React, { useState } from 'react';

function App() {
  // Navigation & Authentication state
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // Architecture Simulation state
  const [logs, setLogs] = useState([
    "🟢 [SYSTEM] Services initialized successfully.",
    "🛡️ [WAF] Security profiles loaded."
  ]);
  const [replicaCount, setReplicaCount] = useState(0);

  // --- SUBMIT LOGIN ---
  const handleLogin = (e) => {
    e.preventDefault();
    if (email && password) {
      setIsLoggedIn(true);
    } else {
      alert("Please fill in your credentials to clear security access.");
    }
  };

  // --- BACKEND SIMULATION TRIGGER ---
  const handleCheckout = async () => {
    setLogs(prev => [...prev, "🌟 [Client] Disbursed payroll / triggered transactional cycle."]);
    
    try {
      const response = await fetch('http://localhost:5000/api/checkout', { method: 'POST' });
      const result = await response.json();
      
      setLogs(prev => [
        ...prev, 
        `✅ API Gateway Gateway cleared. Request Status: ${result.message}`,
        `💾 Mirrored to Master Data Store (ID: ${result.data.id})`
      ]);
      
      setTimeout(fetchAnalytics, 400);
    } catch (error) {
      setLogs(prev => [...prev, "❌ Connection error reaching API Gateway."]);
    }
  };

  const fetchAnalytics = async () => {
    try {
      const response = await fetch('http://localhost:5000/api/analytics');
      const data = await response.json();
      setReplicaCount(data.length);
    } catch (error) {
      console.error(error);
    }
  };

  // ==========================================
  // RENDER SCREEN 1: SECURE SIGN-IN INTERFACE
  // ==========================================
  if (!isLoggedIn) {
    return (
      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', minHeight: '100vh', backgroundColor: '#070b12', color: '#ffffff', fontFamily: 'sans-serif' }}>
        {/* Left Branding Panel */}
        <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', padding: '0 10%', backgroundColor: '#04070d', borderRight: '1px solid #111a2e' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px', marginBottom: '40px' }}>
            <div style={{ width: '32px', height: '32px', backgroundColor: '#00e699', borderRadius: '6px', display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#000', fontWeight: 'bold' }}>F</div>
            <div>
              <div style={{ fontWeight: 'bold', letterSpacing: '1px', fontSize: '16px' }}>FinMark</div>
              <div style={{ fontSize: '10px', color: '#4b5975', tracking: '2px' }}>CORPORATION</div>
            </div>
          </div>

          <div style={{ display: 'inline-block', color: '#00e699', border: '1px solid #00e699', padding: '4px 12px', borderRadius: '20px', fontSize: '11px', width: 'fit-content', marginBottom: '20px', textTransform: 'uppercase', letterSpacing: '1px' }}>
            ⟳ BIR-Compliant • SEC-Registered • PH
          </div>

          <h1 style={{ fontSize: '48px', fontWeight: '700', margin: '0 0 20px 0', lineHeight: '1.1' }}>
            Finance Ops,<br />
            <span style={{ color: '#00e699' }}>built for SEA.</span>
          </h1>

          <p style={{ color: '#4b5975', fontSize: '15px', lineHeight: '1.6', maxWidth: '400px', marginBottom: '40px' }}>
            Cloud-based payroll, accounting, tax computation, and regulatory reporting — purpose-built for SMEs across Southeast Asia.
          </p>

          <div style={{ display: 'flex', gap: '30px', color: '#4b5975', fontSize: '12px', fontWeight: 'bold' }}>
            <div>SME <span style={{ block: 'true', fontWeight: 'normal', color: '#24324d' }}>FOCUSED</span></div>
            <div>SEA <span style={{ block: 'true', fontWeight: 'normal', color: '#24324d' }}>COVERAGE</span></div>
            <div>100% <span style={{ block: 'true', fontWeight: 'normal', color: '#24324d' }}>CLOUD</span></div>
          </div>
        </div>

        {/* Right Input Panel */}
        <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', padding: '0 15%' }}>
          <div style={{ maxWidth: '400px', width: '100%' }}>
            <span style={{ color: '#00e699', textTransform: 'uppercase', fontSize: '11px', letterSpacing: '2px', fontWeight: 'bold' }}>Secure Access</span>
            <h2 style={{ fontSize: '32px', margin: '10px 0' }}>Sign in to your account</h2>
            <p style={{ color: '#4b5975', fontSize: '14px', marginBottom: '30px' }}>Welcome back to FinMark. Your finance operations await.</p>

            <form onSubmit={handleLogin} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
              <div>
                <label style={{ display: 'block', color: '#4b5975', fontSize: '11px', textTransform: 'uppercase', marginBottom: '8px', fontWeight: 'bold' }}>Email Address</label>
                <input 
                  type="email" 
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  style={{ width: '100%', padding: '14px', backgroundColor: '#0d1527', border: '1px solid #1e2d4a', borderRadius: '8px', color: '#fff', fontSize: '14px', outline: 'none' }} 
                  placeholder="name@company.com"
                  required
                />
              </div>

              <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '8px' }}>
                  <label style={{ color: '#4b5975', fontSize: '11px', textTransform: 'uppercase', fontWeight: 'bold' }}>Password</label>
                  <a href="#forgot" style={{ color: '#00e699', fontSize: '11px', textDecoration: 'none', fontWeight: 'bold' }}>Forgot Password?</a>
                </div>
                <input 
                  type="password" 
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  style={{ width: '100%', padding: '14px', backgroundColor: '#0d1527', border: '1px solid #1e2d4a', borderRadius: '8px', color: '#fff', fontSize: '14px', outline: 'none' }} 
                  placeholder="••••••••"
                  required
                />
              </div>

              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', fontSize: '13px', color: '#4b5975', margin: '5px 0' }}>
                <input type="checkbox" id="remember" style={{ accentColor: '#00e699' }} />
                <label htmlFor="remember">Keep me signed in for 30 days</label>
              </div>

              <button type="submit" style={{ width: '100%', padding: '14px', backgroundColor: '#00e699', color: '#04070d', border: 'none', borderRadius: '8px', fontSize: '15px', fontWeight: 'bold', cursor: 'pointer', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '8px' }}>
                Sign In &rarr;
              </button>
            </form>
          </div>
        </div>
      </div>
    );
  }

  // ==========================================
  // RENDER SCREEN 2: CORPORATE BUSINESS DASHBOARD
  // ==========================================
  return (
    <div style={{ backgroundColor: '#070b12', color: '#ffffff', minHeight: '100vh', fontFamily: 'sans-serif', padding: '40px' }}>
      
      {/* Header Bar */}
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px' }}>
        <div>
          <h2 style={{ margin: 0, fontSize: '28px', fontWeight: '600' }}>Good morning, Mighty</h2>
          <p style={{ margin: '5px 0 0 0', color: '#4b5975', fontSize: '14px' }}>Thursday, June 4, 2026 • Here's your business overview</p>
        </div>
        <button onClick={() => setIsLoggedIn(false)} style={{ padding: '8px 16px', backgroundColor: '#1e2d4a', color: '#fff', border: 'none', borderRadius: '6px', cursor: 'pointer', fontSize: '13px' }}>
          Logout Session
        </button>
      </div>

      {/* Top Cards Grid */}
      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr', gap: '20px', marginBottom: '40px' }}>
        <div style={{ backgroundColor: '#0d1527', border: '1px solid #16223b', padding: '20px', borderRadius: '12px' }}>
          <div style={{ fontSize: '12px', color: '#4b5975', fontWeight: '600', marginBottom: '8px' }}>TOTAL REVENUE</div>
          <div style={{ fontSize: '24px', fontWeight: 'bold' }}>₱2,418,300</div>
          <div style={{ fontSize: '12px', color: '#00e699', marginTop: '6px' }}>↑ +18.4%</div>
        </div>
        <div style={{ backgroundColor: '#0d1527', border: '1px solid #16223b', padding: '20px', borderRadius: '12px' }}>
          <div style={{ fontSize: '12px', color: '#4b5975', fontWeight: '600', marginBottom: '8px' }}>TOTAL EXPENSES</div>
          <div style={{ fontSize: '24px', fontWeight: 'bold' }}>₱1,203,440</div>
          <div style={{ fontSize: '12px', color: '#ff4d4d', marginTop: '6px' }}>↘ +6.2%</div>
        </div>
        <div style={{ backgroundColor: '#0d1527', border: '1px solid #16223b', padding: '20px', borderRadius: '12px' }}>
          <div style={{ fontSize: '12px', color: '#4b5975', fontWeight: '600', marginBottom: '8px' }}>NET INCOME</div>
          <div style={{ fontSize: '24px', fontWeight: 'bold' }}>₱1,214,860</div>
          <div style={{ fontSize: '12px', color: '#00e699', marginTop: '6px' }}>↑ +31.7%</div>
        </div>
        <div style={{ backgroundColor: '#0d1527', border: '1px solid #16223b', padding: '20px', borderRadius: '12px' }}>
          <div style={{ fontSize: '12px', color: '#4b5975', fontWeight: '600', marginBottom: '8px' }}>OUTSTANDING AR</div>
          <div style={{ fontSize: '24px', fontWeight: 'bold' }}>₱284,500</div>
          <div style={{ fontSize: '12px', color: '#ff4d4d', marginTop: '6px' }}>↘ 3 overdue</div>
        </div>
      </div>

      {/* Main Split Interface Area */}
      <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '30px' }}>
        
        {/* Left Side: Domain Modules */}
        <div>
          <h3 style={{ color: '#4b5975', textTransform: 'uppercase', fontSize: '12px', letterSpacing: '1px', marginBottom: '20px' }}>Modules</h3>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
            
            {/* Books */}
            <div style={{ backgroundColor: '#0c1a30', border: '1px solid #1a365d', padding: '24px', borderRadius: '12px', cursor: 'pointer' }}>
              <div style={{ color: '#3182ce', fontSize: '20px', marginBottom: '10px' }}>📘</div>
              <h4 style={{ margin: '0 0 5px 0', fontSize: '18px' }}>FinMark Books</h4>
              <p style={{ margin: 0, color: '#4b5975', fontSize: '13px', lineHeight: '1.4' }}>Accounting & Invoicing chart of accounts, journal entries, and financial reports.</p>
            </div>

            {/* Payroll (Hooked into backend script trigger!) */}
            <div onClick={handleCheckout} style={{ backgroundColor: '#09261d', border: '1px solid #134e3a', padding: '24px', borderRadius: '12px', cursor: 'pointer', transition: 'transform 0.2s' }}>
              <div style={{ color: '#00e699', fontSize: '20px', marginBottom: '10px' }}>👥</div>
              <h4 style={{ margin: '0 0 5px 0', fontSize: '18px', display: 'flex', justifyContent: 'space-between' }}>
                FinMark Payroll <span style={{ fontSize: '11px', background: '#b45309', color: '#fff', padding: '2px 8px', borderRadius: '10px' }}>Run Simulation</span>
              </h4>
              <p style={{ margin: 0, color: '#4b5975', fontSize: '13px', lineHeight: '1.4' }}>Employee records, payroll execution, payslips, and automated statutory calculations.</p>
            </div>

            {/* TaxEdge */}
            <div style={{ backgroundColor: '#1a102f', border: '1px solid #3c245c', padding: '24px', borderRadius: '12px' }}>
              <div style={{ color: '#805ad5', fontSize: '20px', marginBottom: '10px' }}>📄</div>
              <h4 style={{ margin: '0 0 5px 0', fontSize: '18px' }}>FinMark TaxEdge</h4>
              <p style={{ margin: 0, color: '#4b5975', fontSize: '13px', lineHeight: '1.4' }}>BIR Form submissions, tax calendars, automated formatting and digital tracking.</p>
            </div>

            {/* Insights (Hooked into live replica state count!) */}
            <div style={{ backgroundColor: '#261a0d', border: '1px solid #4d3319', padding: '24px', borderRadius: '12px' }}>
              <div style={{ color: '#dd6b20', fontSize: '20px', marginBottom: '10px' }}>📊</div>
              <h4 style={{ margin: '0 0 5px 0', fontSize: '18px' }}>FinMark Insights</h4>
              <p style={{ margin: '0 0 10px 0', color: '#4b5975', fontSize: '13px', lineHeight: '1.4' }}>Business KPIs, revenue analytics, and pipeline forecasting.</p>
              <div style={{ background: '#110c05', padding: '10px', borderRadius: '6px', border: '1px solid #332211', fontSize: '13px' }}>
                <span style={{ color: '#dd6b20', fontWeight: 'bold' }}>Read Replica Data Store Vol:</span> {replicaCount} records
              </div>
            </div>

          </div>
        </div>

        {/* Right Side: Activity Log / Live Spine Monitor */}
        <div>
          <h3 style={{ color: '#4b5975', textTransform: 'uppercase', fontSize: '12px', letterSpacing: '1px', marginBottom: '20px' }}>Activity & Alerts (Live Bus Tracker)</h3>
          <div style={{ backgroundColor: '#0d1527', border: '1px solid #16223b', borderRadius: '12px', padding: '20px', minHeight: '300px' }}>
            <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
              {logs.slice(-5).reverse().map((log, index) => (
                <div key={index} style={{ backgroundColor: '#04070d', borderLeft: '3px solid #00e699', padding: '10px 14px', borderRadius: '4px', fontFamily: 'monospace', fontSize: '12px', color: '#e2e8f0', lineHeight: '1.4' }}>
                  {log}
                </div>
              ))}
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default App;
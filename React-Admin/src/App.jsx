import React from 'react'
import { ColorModeContext, useMode } from './theme'
import { CssBaseline, ThemeProvider } from '@mui/material'
import Topbar from './scenes/global/Topbar'
import {Routes, Route} from "react-router-dom"
import Dashboard from './scenes/dashboard/index'
import Sidebar from './scenes/global/Sidebar'
import Team from './scenes/team'
import Scholarships from './scenes/Scholarships'
import Login from './components/Login'
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [theme, colorMode] = useMode();
  return (
    <ColorModeContext.Provider value={colorMode}>
    <ThemeProvider theme={theme}>
    <CssBaseline/>
    <div className="app">
      {window.location.pathname  != '/login' ? <Sidebar/> :undefined}
      <main className='content' >
        {window.location.pathname != '/login' ? <Topbar/>:undefined}
        <Routes>
          <Route exact path='/login' element={<Login/>}/>
          <Route exact path="/" element={<Dashboard/>} />
          <Route exact path="/users" element={<Team/>} />
          <Route exact path="/scholarships" element={<Scholarships/>} />
          {/* <Route path="/invoices" element={<Invoices/>} /> */}
          {/* <Route path="/form" element={<Form/>} /> */}
          {/* <Route path="/bar" element={<Bar/>} /> */}
          {/* <Route path="/pie" element={<Pie/>} /> */}
          {/* <Route path="/line" element={<Line/>} /> */}
          {/* <Route path="/faq" element={<FAQ/>} /> */}
          {/* <Route path="/geography" element={<Geography/>} /> */}
          {/* <Route path="/calendar" element={<Calendar/>} /> */}
        </Routes>
      </main>
    </div>
    </ThemeProvider>
  </ColorModeContext.Provider>
    
  )
}

export default App

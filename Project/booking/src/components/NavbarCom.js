import React from 'react'

import { Navbar, Nav, Container } from 'react-bootstrap'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom'
import { About } from './About'
import { Login } from './Login'
import { Home } from './Home'
import { Signup } from './Signup'

function NavbarCom() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar bg="dark" variant="dark">
          <Container>
            <Navbar.Brand href="/">BOOKING SYSTEM</Navbar.Brand>
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/">
                Home
              </Nav.Link>

              <Nav.Link as={Link} to="/about">
                About
              </Nav.Link>
              <Nav.Link as={Link} to="/login">
                Login
              </Nav.Link>
            </Nav>
          </Container>
        </Navbar>
        <></>
        <Routes>
          <Route path="/about" element={<About />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/navbarCom" element={<NavbarCom />} />
          <Route path="/" element={<Home />} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}
export default NavbarCom

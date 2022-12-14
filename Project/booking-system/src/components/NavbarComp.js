import React, { Component } from 'react'
import {
  Navbar,
  Nav,
  NavDropdown,
  Form,
  Container,
  Button,
} from 'react-bootstrap'
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom'
import About from './About'
import Contact from './Contact'
import Home from './Home'
export default class NavbarComp extends Component {
  render() {
    return (
      <Router>
        <div>
          <Navbar expand="lg" bg="dark" variant="dark">
            <Container fluid>
              <Navbar.Brand href="#">Book System</Navbar.Brand>
              <Navbar.Toggle aria-controls="navbarScroll" />
              <Navbar.Collapse id="navbarScroll">
                <Nav
                  className="me-auto my-4 my-lg-0"
                  style={{ maxHeight: '100px' }}
                  navbarScroll
                >
                  <Nav.Link as={Link} to={'/home'}>
                    Home
                  </Nav.Link>
                  <Nav.Link as={Link} to={'/about'}>
                    About
                  </Nav.Link>
                  <Nav.Link as={Link} to={'/contact'}>
                    Contact
                  </Nav.Link>
                  <NavDropdown title="Link" id="navbarScrollingDropdown">
                    <NavDropdown.Item href="#action3">Action</NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      Another action
                    </NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item href="#action5">
                      Something else here
                    </NavDropdown.Item>
                  </NavDropdown>
                </Nav>
                <Form className="d-flex">
                  <Form.Control
                    type="search"
                    placeholder="Search"
                    className="me-2"
                    aria-label="Search"
                  />
                  <Button variant="outline-success">Search</Button>
                </Form>
              </Navbar.Collapse>
            </Container>
          </Navbar>
        </div>
        <div>
          <Switch>
            <Route path="/contact">
              <Contact />
            </Route>
            <Route path="/about">
              <About />
            </Route>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </div>
      </Router>
    )
  }
}

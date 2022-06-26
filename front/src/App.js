import React from 'react';
import '@fontsource/roboto/300.css';
import BrowseComponent from './components/BrowseComponent';
import FavComponent from './components/FavComponent';
import ProfileComponent from './components/ProfileComponent'
import LoginForm from './components/LoginForm';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import configData from './config.json'

import {
  BrowserRouter as Router,
  Routes, Route, Link
} from 'react-router-dom'

class App extends React.Component {

  constructor(props){
    super(props)
    this.state = {
      albums: [],
    }
  }
  
  testFetch(e){
    if(e.key !== 'Enter') return null

    this.setState({
      albums: []
    })

    let inputSearch = document.getElementById('searchField').value;

    const fetch = require('node-fetch');
    const FormData = require('form-data');
    const form = new FormData();
    form.append('grant_type', 'client_credentials');

      fetch(configData.SERVER_URL + '/search?' + 
        new URLSearchParams({
          q: inputSearch
        }), {
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
      })
      .then(res => {
        if(res.ok){
          res.json().then(json => {
            json.map((item, i) => {
              let artists = item.artists.join()
              let img_src = item.img_src
              let album = {
                key: item.id,
                title: item.title,
                artists: artists,
                img_src: img_src,
                year: item.year,
                itemType: item.type
              }
              this.state.albums.push(album)
              return album
            })
            this.setState({})
          })
        }
        else{
          this.setState({isLoggedIn: false})
        }
      })
      
  }

  render() {
    return (
      <Router>
      <Box justifyContent="center">
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" style={{ background: '#2f9c4c' }}>
              <Toolbar>
                <Typography
                  variant="h5"
                  noWrap
                  component="div"
                  sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' } }}
                >
                  jTru
                </Typography>

                <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                  <Link to="/">
                    <Button sx={{ my: 2, color: 'white', display: 'block' }}>
                      browse
                    </Button>
                  </Link>
                  <Link to="/fav">
                    <Button sx={{ my: 2, color: 'white', display: 'block' }}>
                        fav
                    </Button>
                  </Link>
                  <Link to="/profile">
                    <Button sx={{ my: 2, color: 'white', display: 'block' }}>
                      profile 
                    </Button>
                  </Link>
                    <Button sx={{ my: 2, color: 'white', display: 'block' }} 
                      onClick={() => {
                        fetch(configData.SERVER_URL + "/login", {
                          method: "POST",
                          credentials: 'include',
                          headers: {
                            'Authorization': 'Basic logout'
                          }
                        })
                        .then((response) => {
                          this.setState({isLoggedIn: false})
                        })
                      }}
                    >
                      logout
                    </Button>
                </Box>

              </Toolbar>
            </AppBar>
          </Box>

          { !this.state.isLoggedIn &&
            <LoginForm loginCallback={responseOk => { 
                this.setState({
                  isLoggedIn: responseOk,
                })
              }
            }/>
          }

          { this.state.isLoggedIn &&
          <Routes>
            <Route path="/" element={
              <BrowseComponent 
                albums={this.state.albums}
                action={(e) => this.testFetch(e)} 
              />}
            />
            <Route path="/fav" element={<FavComponent/>}/>
            <Route path="/profile" element={<ProfileComponent/>}/>
          </Routes>
          }

      </Box>
      </Router>
    );
  }
}

export default App;
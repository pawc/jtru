import React, {useState} from 'react';
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

export default function App() {

  const [isLoggedIn, setLoggedIn] = useState(false)
  const [albums, setAlbums] = useState([])
  const [favs, setFavs] = useState([])
  
  const search = e => {
    if(e.key !== 'Enter') return null

    setAlbums([])

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
          let resAlbums = []
          res.json().then(json => {
            json.map((item, i) => {
              let album = {
                key: item.itemKey,
                title: item.title,
                artists: item.artists,
                img_src: item.img_src,
                year: item.year,
                itemType: item.type,
                fav: item.fav
              }
              resAlbums.push(album)
              return album
            })
            setAlbums(resAlbums)
          })
        }
        else{
          setLoggedIn(false)
        }
      })
      
  }

  const getFavs = () => {
    setFavs([])

    fetch(configData.SERVER_URL + '/myReviews?',
    {
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    })
    .then(res => {
      if(res.ok){
        let resFavs = []
        res.json().then(json => {
          json.map((review, i) => {
            let item = review.item
            item.key = item.itemKey
            resFavs.push(item)
            return item
          })
          setFavs(resFavs)
        })
      }
      else{
        setLoggedIn(false)
      }
    })
  }

  const unFav = (unFavItemKey) => {
    let found = favs.filter(item => item.itemKey === unFavItemKey)[0]
    let index = favs.indexOf(found)
    let newFavs = [...favs]
    newFavs.splice(index, 1)
    setFavs(newFavs)
  }

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
                <Link to="/" onClick={() => setAlbums([])}>
                  <Button sx={{ my: 2, color: 'white', display: 'block' }}>
                    browse
                  </Button>
                </Link>
                <Link to="/fav" onClick={getFavs}>
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
                        setLoggedIn(false)
                      })
                    }}
                  >
                    logout
                  </Button>
              </Box>

            </Toolbar>
          </AppBar>
        </Box>

        { !isLoggedIn &&
          <LoginForm loginCallback={responseOk => { 
              setLoggedIn(responseOk)
            }
          }/>
        }

        { isLoggedIn &&
        <Routes>
          <Route path="/" element={
            <BrowseComponent 
              albums={albums}
              action={(e) => search(e)} 
            />}
          />
          <Route path="/fav" element={<FavComponent albums={favs} unFav={unFav}/>}/>
          <Route path="/profile" element={<ProfileComponent/>}/>
        </Routes>
        }

    </Box>
    </Router>
  );

}
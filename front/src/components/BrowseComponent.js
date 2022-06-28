import React from 'react'
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import MyCard from './MyCard';

export default function BrowseComponent(props){
    return (
        <div>
            <Grid
                container
                spacing={0}
                direction="column"
                alignItems="center"
                justifyContent="center"
            >
              <TextField id="searchField" 
                  label="Search anything..." 
                  variant="outlined" 
                  margin="normal"
                  onKeyPress={props.action}
              />

            </Grid>

            <Grid
                container
            >

              { props.albums.map((a, i) => {
                  return (
                    <MyCard 
                      key = {a.key}
                      itemKey = {a.key}
                      title = {a.title} 
                      img_src = {a.img_src}
                      artists = {a.artists}
                      year = {a.year}
                      itemType = {a.itemType}
                      fav = {a.fav}
                      unFav = {() => {}}
                    />
                )})
              }       

            </Grid>
        </div>

    )
}
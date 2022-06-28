import React from 'react'
import Grid from '@mui/material/Grid';
import MyCard from './MyCard';

export default function FavComponent(props){

    return (
        <div>
            <Grid container>

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
                    />
                )})
              }       

            </Grid>
        </div>
    )
}
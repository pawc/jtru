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
                      key = {a.itemKey}
                      itemKey = {a.itemKey}
                      title = {a.title} 
                      img_src = {a.img_src}
                      artists = {a.artists}
                      year = {a.year}
                      itemType = {a.type}
                      fav = {a.fav}
                      unFav = {props.unFav}
                      stars = {a.stars}
                    />
                )})
              }       

            </Grid>
        </div>
    )
}
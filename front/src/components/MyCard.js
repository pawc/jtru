import React, {useState} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea, Grid } from '@mui/material';
import ThumbUpOffAlt from '@mui/icons-material/ThumbUpOffAlt';
import ThumbUpAlt from '@mui/icons-material/ThumbUpAlt';
import configData from '../config.json'
import Rating from '@mui/material/Rating'

export default function MyCard(props) {

  const [itemType, setItemType] = useState(props.itemType)
  const [key, setKey] = useState(props.itemKey)
  const [artists, setArtists] = useState(props.artists)
  const [title, setTitle] = useState(props.title)
  const [img_src, setImg_src] = useState(props.img_src)
  const [year, setYear] = useState(props.year)
  const [isFav, setFav] = useState(props.fav)
  const [stars, setStars] = useState(props.stars)

  let album = {
    id: 0,
    user: {
      id: 0,
      email: null,
      password: null
    },
    item: {
      itemKey: key,
      type: itemType,
      artists: artists,
      title: title,
      img_src: img_src,
      year: year
    }
  }

  const toggleFav = () => {

    setFav(wasFav => {
      if(stars === 0 && wasFav){
        console.log('okokok')
        props.unFav(props.itemKey)
      }
      album = {...album, stars: stars, fav: !wasFav}
     
      fetch(configData.SERVER_URL + '/review', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(album)
      })
      return !wasFav
    })
    

  }

  const changeStars = (st) => {
  
    album = {...album, stars: st, fav: isFav}

    fetch(configData.SERVER_URL + '/review', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include',
      body: JSON.stringify(album)
    })

  }

  return (
    <Card sx={{ maxWidth: 345, margin: 5, display: 'inline-block'}}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="140"
          image={props.img_src}
        />
        <CardContent>
          <Typography  align="center" variant="h6" component="div">
            {props.title}
          </Typography>
          <Typography variant="subtitle1"  align="center" color="text.secondary">
            {props.artists}
          </Typography>
          <Grid container spacing={2}>
            <Grid item xs={4}>
              <Typography align="center" variant="subtitle2" color="text.secondary">
                {props.year}
              </Typography>
            </Grid>
            <Grid item xs={4}>
              <Typography align="center" variant="subtitle2" color="text.secondary">
                {props.itemType}
              </Typography>
            </Grid>
            <Grid item xs={4}>
              <Typography align="right">
                { !isFav &&
                  <ThumbUpOffAlt sx={{color: "#1f6933"}} onClick={toggleFav}/>
                }
                { isFav &&
                  <ThumbUpAlt sx={{color: "#1f6933"}} onClick={toggleFav}/>
                }
              </Typography>
            </Grid>
          </Grid>
          <Grid item align="center">
            <Rating
            name="simple-controlled"
            value={stars}
            precision={0.5}
            onChange={(event, newValue) => {
              setStars(prev => {
                changeStars(newValue)
                return newValue
              })
            }}/>
          </Grid>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}
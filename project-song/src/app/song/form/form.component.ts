import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Song } from 'src/app/model/song.model';
import { SongService } from 'src/app/service/song.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  songs: Song = new Song();
  title: string = "añadir cancion";
  errores: string[];
  customerService: any;

  constructor(private songService: SongService, private router: Router, private activatedRouter: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadSong()
  }

  create(): void {
    this.songService.create(this.songs).subscribe(
      songs => {
        this.router.navigate(['/song'])
        console.log("creado con exito")
      },
      err => {
        this.errores = err.error.errors as string[];
        console.error('codigo error backend ' + err.status);
        console.error(err.error.errors);
      }
    );
  }
  song(song: any) {
    throw new Error('Method not implemented.');
  }


  loadSong(): void {
    this.activatedRouter.params.subscribe(params => {
      let id = params['id']
      if (id) {
        this.songService.getSong(id).subscribe((songs) => this.songs = songs)
      }
    })
  }

  update(): void{
    this.customerService.update(this.songs).subscribe( json => {
      this.router.navigate(['/songs'])
     
    })
  }
}

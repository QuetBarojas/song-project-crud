import { Component, OnInit } from '@angular/core';
import { Song } from '../model/song.model';
import { SongService } from '../service/song.service';

@Component({
  selector: 'app-song',
  templateUrl: './song.component.html',
  styleUrls: ['./song.component.css']
})
export class SongComponent implements OnInit {

  public songs:Song[];

  constructor(private songService:SongService) {}

  ngOnInit(): void {
    this.songService
      .getSongs()
      .subscribe((songs) => (this.songs = songs));
  }

}

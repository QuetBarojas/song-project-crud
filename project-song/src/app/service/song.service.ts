import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { Song } from '../model/song.model';
import { catchError, map } from 'rxjs/operators';



@Injectable({
  providedIn: 'root'
})
export class SongService {

  private urlEndPoint: string = 'http://localhost:8080/api/songs';

  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http:HttpClient ,private router:Router) { }

  getSongs(): Observable<Song[]> {
    return this.http.get<Song[]>(this.urlEndPoint).pipe(
      map((response) => {

        let songs = response as Song[];
        return songs.map(song => {
          song.name = song.name.toUpperCase();
          return song;
        });
      })
    );
  }

  create(song: Song): Observable<Song> {
    return this.http
      .post<Song>(this.urlEndPoint, song, { headers: this.httpHeaders })
      .pipe(
        map((response: any) => response.song as Song),
        catchError((e) => {
          if (e.status == 400) {
            return throwError(e);
          }

          console.error(e.error.mensaje);
          return throwError(e);
        })
      );
  }

  getSong(id): Observable<Song> {
    return this.http.get<Song>(`${this.urlEndPoint}/${id}`).pipe(
      catchError((e) => {
        this.router.navigate(['/songs']);
        console.error(e.error.mensaje);
        return throwError(e);
      })
    );
  }

  update(song: Song): Observable<any> {
    return this.http
      .put<any>(`${this.urlEndPoint}/${song.id}`, song, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          if (e.status == 400) {
            return throwError(e);
          }

          console.error(e.error.mensaje);
          return throwError(e);
        })
      );
  }

  delete(id: number): Observable<Song> {
    return this.http
      .delete<Song>(`${this.urlEndPoint}/${id}`, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          console.error(e.error.mensaje);
          return throwError(e);
        })
      );
  }


}

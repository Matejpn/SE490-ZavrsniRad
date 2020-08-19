import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SizeService {
    private getAllSizes = environment.baseUrl + '/size/all';

    constructor(private http: HttpClient) { }

    getSizes(type: any): Observable<any> {
        return this.http.get<any>(this.getAllSizes, {
            params: { type }
        });
    }
}
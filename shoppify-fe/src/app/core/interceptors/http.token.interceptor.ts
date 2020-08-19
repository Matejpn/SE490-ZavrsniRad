import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
	constructor() {}
	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		const headers = new HttpHeaders({
			Authorization: `Bearer ${localStorage.getItem('token')}`
		});
		const authReq = request.clone({ headers });
		return next.handle(authReq);
	}
}

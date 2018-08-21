import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {RestService} from '../../rest-service/rest.service';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    path = 'http://localhost:8080/';

    constructor(private restService: RestService) {
    }

    pushFileToStorage(file: File): Observable<any> {
        // const formdata: FormData = new FormData();
        //
        // formdata.append('file', file);
        //
        // return this.http.post(this.path, formdata, {
        //   reportProgress: true,
        //   responseType: 'text'
        // });

        // return this.postFile(this.path + 'profile/avatar', file);
        return null;
    }
}

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {BaseService} from '../../../services/base.service';
import {AccountModel} from '../../../models/account.model';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    path = '/profile';
    profile: AccountModel;

    constructor(private baseService: BaseService) {
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

    getProfile() {
        this.baseService.get(this.path).subscribe(data => {
            console.log(data.json);
            this.profile = data.json();
        })
    }
}

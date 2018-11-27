import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {BaseService} from '../../../services/base.service';
import {AccountModel} from '../../../models/profile/account.model';

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    path = '/profile';
    profile: AccountModel;

    constructor(private baseService: BaseService) {
    }

    getUserProfile() {
        this.baseService.get(this.path).subscribe(data => {
            this.profile = data.json();
        })
    }

    getProfile(id: number): Observable<AccountModel> {
        return this.baseService.mapJSON(this.baseService.get(this.path + '/' + id));
    }

    getNickById(id: number): Observable<string> {
        return this.baseService.mapTEXT(this.baseService.get(this.path + '/nick/' + id));
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

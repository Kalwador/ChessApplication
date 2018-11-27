import {AppProfile} from "./app-profile.enum";

export class AppInfoModel {
    projectName: string;
    projectDescription: string;
    version: string;
    profile: AppProfile;
}
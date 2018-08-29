enum GenderModel {
    MALE,
    FEMALE
}

export class AccountModel {
    public id: number;
    public username: string;
    public email: string;
    public firstName: string;
    public lastName: string;
    public age: number;
    public gender: GenderModel;
    // public avatar: byte[];
    public isFirstLogin: boolean;
}   
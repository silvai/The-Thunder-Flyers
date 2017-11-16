import {UserMode} from "./usermode";

export class User {
    constructor (
        public firstName: string,
        public lastName: string,
        public username: string,
        public pass: string,
        public userMode: UserMode
    ) {}
}
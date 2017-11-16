import {UserMode} from "./usermode";

export class User {
    constructor (
        public username: string,
        public pass: string,
        public firstName?: string,
        public lastName?: string,
        public userMode?: UserMode
    ) {}
}
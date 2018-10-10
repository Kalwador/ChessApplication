import {NgModule} from '@angular/core';
import {
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    MatCardModule,
    // MatSlider,

} from '@angular/material';

const modules = [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatCardModule,
    // MatSlider
];

@NgModule({
    imports: [modules],
    exports: [modules]
})
export class MaterialModule {}



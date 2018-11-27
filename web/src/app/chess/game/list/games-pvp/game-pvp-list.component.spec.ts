import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamePvpListComponent } from './game-pvp-list.component';

describe('GamePvpListComponent', () => {
  let component: GamePvpListComponent;
  let fixture: ComponentFixture<GamePvpListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamePvpListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamePvpListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

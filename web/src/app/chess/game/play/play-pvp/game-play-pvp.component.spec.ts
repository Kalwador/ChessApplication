import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamePlayPvpComponent } from './game-play-pvp.component';

describe('GamePlayPvpComponent', () => {
  let component: GamePlayPvpComponent;
  let fixture: ComponentFixture<GamePlayPvpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamePlayPvpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamePlayPvpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

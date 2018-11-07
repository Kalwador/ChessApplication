import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GameNewOptionsComponent } from './game-new-options.component';

describe('GameNewOptionsComponent', () => {
  let component: GameNewOptionsComponent;
  let fixture: ComponentFixture<GameNewOptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GameNewOptionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GameNewOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

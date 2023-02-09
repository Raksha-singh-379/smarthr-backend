import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReEnumerationDetailComponent } from './re-enumeration-detail.component';

describe('ReEnumeration Management Detail Component', () => {
  let comp: ReEnumerationDetailComponent;
  let fixture: ComponentFixture<ReEnumerationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReEnumerationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reEnumeration: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReEnumerationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReEnumerationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reEnumeration on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reEnumeration).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

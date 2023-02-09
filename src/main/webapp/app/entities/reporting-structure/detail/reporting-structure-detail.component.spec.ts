import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportingStructureDetailComponent } from './reporting-structure-detail.component';

describe('ReportingStructure Management Detail Component', () => {
  let comp: ReportingStructureDetailComponent;
  let fixture: ComponentFixture<ReportingStructureDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReportingStructureDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ reportingStructure: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReportingStructureDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReportingStructureDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load reportingStructure on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.reportingStructure).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

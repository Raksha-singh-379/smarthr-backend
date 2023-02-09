import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IWorkinDaysSetting } from '../workin-days-setting.model';
import { WorkinDaysSettingService } from '../service/workin-days-setting.service';

import { WorkinDaysSettingRoutingResolveService } from './workin-days-setting-routing-resolve.service';

describe('WorkinDaysSetting routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: WorkinDaysSettingRoutingResolveService;
  let service: WorkinDaysSettingService;
  let resultWorkinDaysSetting: IWorkinDaysSetting | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(WorkinDaysSettingRoutingResolveService);
    service = TestBed.inject(WorkinDaysSettingService);
    resultWorkinDaysSetting = undefined;
  });

  describe('resolve', () => {
    it('should return IWorkinDaysSetting returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWorkinDaysSetting = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultWorkinDaysSetting).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWorkinDaysSetting = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultWorkinDaysSetting).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IWorkinDaysSetting>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultWorkinDaysSetting = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultWorkinDaysSetting).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

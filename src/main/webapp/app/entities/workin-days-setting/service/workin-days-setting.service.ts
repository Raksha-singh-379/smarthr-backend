import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IWorkinDaysSetting, NewWorkinDaysSetting } from '../workin-days-setting.model';

export type PartialUpdateWorkinDaysSetting = Partial<IWorkinDaysSetting> & Pick<IWorkinDaysSetting, 'id'>;

type RestOf<T extends IWorkinDaysSetting | NewWorkinDaysSetting> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestWorkinDaysSetting = RestOf<IWorkinDaysSetting>;

export type NewRestWorkinDaysSetting = RestOf<NewWorkinDaysSetting>;

export type PartialUpdateRestWorkinDaysSetting = RestOf<PartialUpdateWorkinDaysSetting>;

export type EntityResponseType = HttpResponse<IWorkinDaysSetting>;
export type EntityArrayResponseType = HttpResponse<IWorkinDaysSetting[]>;

@Injectable({ providedIn: 'root' })
export class WorkinDaysSettingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/workin-days-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(workinDaysSetting: NewWorkinDaysSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workinDaysSetting);
    return this.http
      .post<RestWorkinDaysSetting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(workinDaysSetting: IWorkinDaysSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workinDaysSetting);
    return this.http
      .put<RestWorkinDaysSetting>(`${this.resourceUrl}/${this.getWorkinDaysSettingIdentifier(workinDaysSetting)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(workinDaysSetting: PartialUpdateWorkinDaysSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(workinDaysSetting);
    return this.http
      .patch<RestWorkinDaysSetting>(`${this.resourceUrl}/${this.getWorkinDaysSettingIdentifier(workinDaysSetting)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestWorkinDaysSetting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestWorkinDaysSetting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getWorkinDaysSettingIdentifier(workinDaysSetting: Pick<IWorkinDaysSetting, 'id'>): number {
    return workinDaysSetting.id;
  }

  compareWorkinDaysSetting(o1: Pick<IWorkinDaysSetting, 'id'> | null, o2: Pick<IWorkinDaysSetting, 'id'> | null): boolean {
    return o1 && o2 ? this.getWorkinDaysSettingIdentifier(o1) === this.getWorkinDaysSettingIdentifier(o2) : o1 === o2;
  }

  addWorkinDaysSettingToCollectionIfMissing<Type extends Pick<IWorkinDaysSetting, 'id'>>(
    workinDaysSettingCollection: Type[],
    ...workinDaysSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const workinDaysSettings: Type[] = workinDaysSettingsToCheck.filter(isPresent);
    if (workinDaysSettings.length > 0) {
      const workinDaysSettingCollectionIdentifiers = workinDaysSettingCollection.map(
        workinDaysSettingItem => this.getWorkinDaysSettingIdentifier(workinDaysSettingItem)!
      );
      const workinDaysSettingsToAdd = workinDaysSettings.filter(workinDaysSettingItem => {
        const workinDaysSettingIdentifier = this.getWorkinDaysSettingIdentifier(workinDaysSettingItem);
        if (workinDaysSettingCollectionIdentifiers.includes(workinDaysSettingIdentifier)) {
          return false;
        }
        workinDaysSettingCollectionIdentifiers.push(workinDaysSettingIdentifier);
        return true;
      });
      return [...workinDaysSettingsToAdd, ...workinDaysSettingCollection];
    }
    return workinDaysSettingCollection;
  }

  protected convertDateFromClient<T extends IWorkinDaysSetting | NewWorkinDaysSetting | PartialUpdateWorkinDaysSetting>(
    workinDaysSetting: T
  ): RestOf<T> {
    return {
      ...workinDaysSetting,
      lastModified: workinDaysSetting.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restWorkinDaysSetting: RestWorkinDaysSetting): IWorkinDaysSetting {
    return {
      ...restWorkinDaysSetting,
      lastModified: restWorkinDaysSetting.lastModified ? dayjs(restWorkinDaysSetting.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestWorkinDaysSetting>): HttpResponse<IWorkinDaysSetting> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestWorkinDaysSetting[]>): HttpResponse<IWorkinDaysSetting[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

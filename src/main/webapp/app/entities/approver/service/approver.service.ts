import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApprover, NewApprover } from '../approver.model';

export type PartialUpdateApprover = Partial<IApprover> & Pick<IApprover, 'id'>;

type RestOf<T extends IApprover | NewApprover> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestApprover = RestOf<IApprover>;

export type NewRestApprover = RestOf<NewApprover>;

export type PartialUpdateRestApprover = RestOf<PartialUpdateApprover>;

export type EntityResponseType = HttpResponse<IApprover>;
export type EntityArrayResponseType = HttpResponse<IApprover[]>;

@Injectable({ providedIn: 'root' })
export class ApproverService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/approvers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(approver: NewApprover): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(approver);
    return this.http
      .post<RestApprover>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(approver: IApprover): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(approver);
    return this.http
      .put<RestApprover>(`${this.resourceUrl}/${this.getApproverIdentifier(approver)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(approver: PartialUpdateApprover): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(approver);
    return this.http
      .patch<RestApprover>(`${this.resourceUrl}/${this.getApproverIdentifier(approver)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestApprover>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestApprover[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getApproverIdentifier(approver: Pick<IApprover, 'id'>): number {
    return approver.id;
  }

  compareApprover(o1: Pick<IApprover, 'id'> | null, o2: Pick<IApprover, 'id'> | null): boolean {
    return o1 && o2 ? this.getApproverIdentifier(o1) === this.getApproverIdentifier(o2) : o1 === o2;
  }

  addApproverToCollectionIfMissing<Type extends Pick<IApprover, 'id'>>(
    approverCollection: Type[],
    ...approversToCheck: (Type | null | undefined)[]
  ): Type[] {
    const approvers: Type[] = approversToCheck.filter(isPresent);
    if (approvers.length > 0) {
      const approverCollectionIdentifiers = approverCollection.map(approverItem => this.getApproverIdentifier(approverItem)!);
      const approversToAdd = approvers.filter(approverItem => {
        const approverIdentifier = this.getApproverIdentifier(approverItem);
        if (approverCollectionIdentifiers.includes(approverIdentifier)) {
          return false;
        }
        approverCollectionIdentifiers.push(approverIdentifier);
        return true;
      });
      return [...approversToAdd, ...approverCollection];
    }
    return approverCollection;
  }

  protected convertDateFromClient<T extends IApprover | NewApprover | PartialUpdateApprover>(approver: T): RestOf<T> {
    return {
      ...approver,
      lastModified: approver.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restApprover: RestApprover): IApprover {
    return {
      ...restApprover,
      lastModified: restApprover.lastModified ? dayjs(restApprover.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestApprover>): HttpResponse<IApprover> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestApprover[]>): HttpResponse<IApprover[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

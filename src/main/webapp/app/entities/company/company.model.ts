import dayjs from 'dayjs/esm';

export interface ICompany {
  id: number;
  companyName?: string | null;
  contactPerson?: string | null;
  postalCode?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  mobileNumber?: string | null;
  websiteUrl?: string | null;
  fax?: string | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  regNumber?: string | null;
  gstin?: string | null;
  pan?: string | null;
  tan?: string | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
